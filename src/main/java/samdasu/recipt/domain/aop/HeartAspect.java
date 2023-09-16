package samdasu.recipt.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

@Slf4j
@org.aspectj.lang.annotation.Aspect
public class HeartAspect {
    @Around("samdasu.recipt.domain.aop.Pointcuts.insertRecipeHeart()")
    public Object insertRecipeHeart(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[기본 레시피 좋아요 +1 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[기본 레시피 좋아요 +1 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[기본 레시피 좋아요 +1 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[기본 레시피 좋아요 +1 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.deleteRecipeHeart()")
    public Object deleteRecipeHeart(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[기본 레시피 좋아요 -1 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[기본 레시피 좋아요 -1 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[기본 레시피 좋아요 -1 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[기본 레시피 좋아요 -1 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.insertRegisterRecipeHeart()")
    public Object insertRegisterRecipeHeart(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[등록 레시피 좋아요 +1 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[등록 레시피 좋아요 +1 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[등록 레시피 좋아요 +1 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[등록 레시피 좋아요 +1 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.deleteRegisterRecipeHeart()")
    public Object deleteRegisterRecipeHeart(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[등록 레시피 좋아요 -1 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[등록 레시피 좋아요 -1 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[등록 레시피 좋아요 -1 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[등록 레시피 좋아요 -1 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.insertReviewHeart()")
    public Object insertReviewHeart(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[리뷰 좋아요 +1 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[리뷰 좋아요 +1 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[리뷰 좋아요 +1 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리뷰 좋아요 +1 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.deleteReviewHeart()")
    public Object deleteReviewHeart(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[리뷰 좋아요 -1 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[리뷰 좋아요 -1 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[리뷰 좋아요 -1 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리뷰 좋아요 -1 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
