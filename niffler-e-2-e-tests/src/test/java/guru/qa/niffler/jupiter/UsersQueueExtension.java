package guru.qa.niffler.jupiter;

import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.TestData;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static guru.qa.niffler.jupiter.User.UserType.*;

public class UsersQueueExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    private static Map<User.UserType, Queue<UserJson>> users = new ConcurrentHashMap<>();

    static {
        Queue<UserJson> friendsQueue = new ConcurrentLinkedQueue<>();
        friendsQueue.add(user("dima", "12345", "duck"));
        friendsQueue.add(user("duck", "12345", "dima"));

        Queue<UserJson> invitationSendedQueue = new ConcurrentLinkedQueue<>();
        invitationSendedQueue.add(user("bee", "12345", "barsik"));

        Queue<UserJson> invitationReceivedQueue = new ConcurrentLinkedQueue<>();
        invitationReceivedQueue.add(user("barsik", "12345", "bee"));
        invitationReceivedQueue.add(user("rabbit", "12345", "bee"));

        users.put(WITH_FRIENDS, friendsQueue);
        users.put(INVITATION_SEND, invitationSendedQueue);
        users.put(INVITATION_RECIEVED, invitationReceivedQueue);
    }

//  @Override
//  public void beforeEach(ExtensionContext context) throws Exception {
//    Parameter[] parameters = context.getRequiredTestMethod().getParameters();
//
//    for (Parameter parameter : parameters) {
//      User annotation = parameter.getAnnotation(User.class);
//      if (annotation != null && parameter.getType().isAssignableFrom(UserJson.class)) {
//        UserJson testCandidate = null;
//        Queue<UserJson> queue = users.get(annotation.value());
//        while (testCandidate == null) {
//          testCandidate = queue.poll();
//        }
//        context.getStore(NAMESPACE).put(context.getUniqueId(), testCandidate);
//        break;
//      }
//    }
//  }

    @Override
    public void beforeEach(ExtensionContext context) {
        List<Method> methods = new ArrayList<>();
        Arrays.stream(context.getRequiredTestClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(BeforeEach.class))
                .forEach(methods::add);
        methods.add(context.getRequiredTestMethod());

        List<Parameter> parameters = methods.stream().map(m -> m.getParameters())
                .flatMap(Arrays::stream)
                .filter(parameter -> parameter.isAnnotationPresent(User.class))
                .filter(parameter -> parameter.getType().isAssignableFrom(UserJson.class))
                .toList();

        Map<User.UserType, UserJson> testUsers = new HashMap<>();

        for (Parameter parameter : parameters) {
            User annotation = parameter.getAnnotation(User.class);
            if (testUsers.containsKey(annotation.value())) {
                continue;
            }
            Queue<UserJson> queue = users.get(annotation.value());
            UserJson testCandidate = null;
            while (testCandidate == null) {
                testCandidate = queue.poll();
            }
            testUsers.put(annotation.value(), testCandidate);

        }
        context.getStore(NAMESPACE).put(context.getUniqueId(), testUsers);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Map<User.UserType, UserJson> usersFromTest = context.getStore(NAMESPACE)
                .get(context.getUniqueId(), Map.class);
        for (User.UserType userType : usersFromTest.keySet()) {
            users.get(userType).add(usersFromTest.get(userType));
        }

    }

//  @Override
//  public void afterTestExecution(ExtensionContext context) throws Exception {
//    UserJson userFromTest = context.getStore(NAMESPACE)
//        .get(context.getUniqueId(), UserJson.class);
//    users.get(userFromTest.testData().friendName()).add(userFromTest);
//  }


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(UserJson.class) &&
                parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return (UserJson) extensionContext.getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), Map.class)
                .get(parameterContext.findAnnotation(User.class).get().value());
    }

//    @Override
//    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
//        return extensionContext.getStore(NAMESPACE)
//                .get(extensionContext.getUniqueId(), UserJson.class);
//    }

    private static UserJson user(String username, String password, String friendName) {
        return new UserJson(
                null,
                username,
                null,
                null,
                CurrencyValues.RUB,
                null,
                null,
                new TestData(
                        password,
                        friendName
                )
        );
    }
}
