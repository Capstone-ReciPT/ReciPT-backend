package samdasu.recipt.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
@Slf4j
@org.aspectj.lang.annotation.Aspect
public class RecipeAspect {
    @Around("samdasu.recipt.domain.aop.Pointcuts.recipeUpdateRatingScore()")
    public Object updateRatingScore(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[기본 레시피 평점 갱신 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[기본 레시피 평점 갱신 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[기본 레시피 평점 갱신 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[기본 레시피 평점 갱신 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.recipeResetViewCount()")
    public Object resetViewCount(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[기본 레시피 조회수 리셋 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[기본 레시피 조회수 리셋 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[기본 레시피 조회수 리셋 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[기본 레시피 조회수 리셋 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.recipeIncreaseViewCount()")
    public Object increaseViewCount(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[기본 레시피 조회수 증가 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[기본 레시피 조회수 증가 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[기본 레시피 조회수 증가 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[기본 레시피 조회수 증가 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }


}
