package samdasu.recipt.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
@Slf4j
@org.aspectj.lang.annotation.Aspect
public class ReviewAspect {
    @Around("samdasu.recipt.domain.aop.Pointcuts.saveRecipeReview()")
    public Object saveRecipeReview(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[기본 레시피 저장 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[기본 레시피 저장 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[기본 레시피 저장 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[기본 레시피 저장 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.saveRegisterRecipeReview()")
    public Object saveRegisterRecipeReview(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[유저 레시피 등록 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[유저 레시피 등록 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[유저 레시피 등록 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[유저 레시피 등록 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.reviewUpdate()")
    public Object reviewUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[등록한 레시피 수정 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[등록한 레시피 수정 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[등록한 레시피 수정 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[등록한 레시피 수정 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.reviewDelete()")
    public Object reviewDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[등록한 레시피 삭제 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[등록한 레시피 삭제  커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[등록한 레시피 삭제 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[등록한 레시피 삭제 릴리즈] {}", joinPoint.getSignature());
        }
    }
}

