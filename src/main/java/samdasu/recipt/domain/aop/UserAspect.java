package samdasu.recipt.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

@Slf4j
@org.aspectj.lang.annotation.Aspect
public class UserAspect {
    @Around("samdasu.recipt.domain.aop.Pointcuts.login()")
    public Object login(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[회원가입 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[회원가입 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[회원가입 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[회원가입 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.userUpdate()")
    public Object update(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[회원정보 업데이트 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[회원정보 업데이트  커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[회원정보 업데이트 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[회원정보 업데이트 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
