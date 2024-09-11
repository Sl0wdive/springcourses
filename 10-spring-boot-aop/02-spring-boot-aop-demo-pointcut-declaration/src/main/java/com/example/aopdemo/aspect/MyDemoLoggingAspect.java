package com.example.aopdemo.aspect;

import com.example.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

    @Around("execution(* com.example.aopdemo.dao.AccountDAO.findAccounts(..))")
    public Object aroundGetFortune(
            ProceedingJoinPoint theProceedingJoinPoint) throws Throwable{
        String method = theProceedingJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @Around on method: " + method);

        long begin = System.currentTimeMillis();

        Object result = null;

        try {
            result = theProceedingJoinPoint.proceed();
        }
        catch (Exception exc) {
            System.out.println(exc.getMessage());

            throw exc;
        }

        long end = System.currentTimeMillis();

        long duration = end - begin;
        System.out.println("\n=====>>> Duration: " + duration /1000.0 + "seconds");

        return result;
    }


    @After("execution(* com.example.aopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountAdvice(JoinPoint theJoinPoint) {

        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @After (finally) on method: " + method);

    }

    @AfterThrowing(
            pointcut = "execution(* com.example.aopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "theExc")
    public void afterThrowingAccountAdvice(
            JoinPoint theJoinPoint, Throwable theExc) {
        String method = theJoinPoint.getSignature().toShortString();

        System.out.println("\n=====>>> Executing @AfterReturning on method: " + method);

        System.out.println("\n=====>>> The exception is : " + theExc);




    }

    @AfterReturning(
            pointcut = "execution(* com.example.aopdemo.dao.AccountDAO.findAccounts(..))",
            returning = "result")
    public void afterReturningFindAccountAdvice(JoinPoint theJoinPoint, List<Account> result){
        String method = theJoinPoint.getSignature().toShortString();

        System.out.println("\n=====>>> Executing @AfterReturning on method: " + method);

        System.out.println("\n=====>>> result is: " + result);

        convertAccountNameToUpperCase(result);

        System.out.println("\n=====>>> result is: " + result);

    }

    private void convertAccountNameToUpperCase(List<Account> result) {

        for (Account tempAccount : result) {
            String theUpperName = tempAccount.getName().toUpperCase();

            tempAccount.setName(theUpperName);
        }
    }

    @Before("com.example.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice(JoinPoint theJointPoint) {
        System.out.println("\n=====>>> Executing @Before advice on method");

        MethodSignature methodSignature = (MethodSignature) theJointPoint.getSignature();

        System.out.println("Method: " + methodSignature);

        Object[] args = theJointPoint.getArgs();

        for (Object tempArg : args) {
            System.out.println(tempArg);

            if (tempArg instanceof Account) {

                Account theAccount = (Account) tempArg;

                System.out.println("account name: " + theAccount.getName());
                System.out.println("account level: " + theAccount.getLevel());
            }
        }
    }
}
