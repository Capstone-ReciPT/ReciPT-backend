package samdasu.recipt.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

@Slf4j
@org.aspectj.lang.annotation.Aspect
public class RegisterRecipeAspect {
    @Around("samdasu.recipt.domain.aop.Pointcuts.RegisterRecipeUpdateRatingScore()")
    public Object updateRatingScore(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[등록한 레시피 평점 갱신 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[등록한 레시피 평점 갱신 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[등록한 레시피 평점 갱신 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[등록한 레시피 평점 갱신 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
    @Around("samdasu.recipt.domain.aop.Pointcuts.registerRecipeSave()")
    public Object registerRecipeSave(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[레시피 등록 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[레시피 등록 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[레시피 등록 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[레시피 등록 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.deleteRegisterRecipe()")
    public Object deleteRegisterRecipe(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[등록한 레시피 삭제 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[등록한 레시피 삭제 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[등록한 레시피 삭제 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[등록한 레시피 삭제 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.RegisterRecipeResetViewCount()")
    public Object resetViewCount(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[등록한 레시피 조회수 리셋 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[등록한 레시피 조회수 리셋 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[등록한 레시피 조회수 리셋 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[등록한 레시피 조회수 리셋 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Around("samdasu.recipt.domain.aop.Pointcuts.RegisterRecipeIncreaseViewCount()")
    public Object increaseViewCount(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[등록한 레시피 조회수 증가 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[등록한 레시피 조회수 증가 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[등록한 레시피 조회수 증가 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[등록한 레시피 조회수 증가 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }


}
