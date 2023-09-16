package samdasu.recipt.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

@Slf4j
@org.aspectj.lang.annotation.Aspect
public class GptAspect {
    @Around("samdasu.recipt.domain.aop.Pointcuts.createGptRecipe()")
    public Object createGptRecipe(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[Gpt 추천 레시피 저장 트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[Gpt 추천 레시피 저장 트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[Gpt 추천 레시피 저장 트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[Gpt 추천 레시피 저장 리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
