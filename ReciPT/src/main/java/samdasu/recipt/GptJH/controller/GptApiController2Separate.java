//package samdasu.recipt.GptJH.controller;
//
//
//import com.google.gson.Gson;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.*;
//import samdasu.recipt.GptJH.controller.dto.RecipeDataJson;
//import samdasu.recipt.GptJH.controller.dto.ResponseModel;
//import samdasu.recipt.GptJH.dto.chat.ChatMessage;
//import samdasu.recipt.GptJH.service.GptService;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Slf4j
//@CrossOrigin
//@RestController
//@RequestMapping("/gpt")
//public class GptApiController2Separate {
//
//    @Autowired
//    private GptService gptService;
//
//    private Map<Integer, List<ChatMessage>> stockConversation = new HashMap<>();
//
//    private List<List<ChatMessage>> stock = new ArrayList<>();
//
//    //    private List<ChatMessage> joined = new ArrayList<>();
//    private Integer count = 0;
//    private String temp;
//    private boolean isFirstExecution = true;
//    private boolean isFirstConversation = true;
//
//    @PostMapping("/end")
//    public void end() {
//        log.info("저장된 대화 내용 삭제 완료!!");
//        stockConversation.clear();
//        stock.clear();
//        count = 0;
//    }
//
//    @PostMapping("/send/recommendfood") //단순 음식 추천
//    public ResponseModel<String> recommendFood(HttpServletRequest request, @RequestBody List<ChatMessage> messages) {
//        String requestId = sendMessageFirst(request, messages);
//        if (CollectionUtils.isEmpty(messages)) {
//            return ResponseModel.fail("messages can not be empty");
//        }
//        String responseMessage = responseMessageFirst(request, messages, requestId);
//        return ResponseModel.success(responseMessage);
//    }
//
//    @PostMapping("/send/conversation") //인터렉티브 시작
//    public ResponseModel<String> conversation(HttpServletRequest request, @RequestParam("foodname") String foodname, @RequestBody List<ChatMessage> messages) {
//        temp = "";
//        List<ChatMessage> joined = new ArrayList<>();
//        joined.clear();
//        log.info("foodname = {}", foodname);
//
//        if (isFirstConversation) {
//            isFirstConversation = false;
//            List<ChatMessage> propmt = new ArrayList<>();
//            propmt.add(new ChatMessage("system", "In your response, provide the necessary ingredients and a recipe for making the " + foodname + ". The recipe should be broken down into steps 1, 2, and so on. Please specify the exact amount of ingredients required in the recipe. ex) 300ml water, 10g salt. \n" +
//                    "All answers must be written in Korean."));
//            joined = Stream.concat(propmt.stream(), messages.stream())
//                    .collect(Collectors.toList());
//        } else {
//            joined = Stream.concat(joined.stream(), messages.stream())
//                    .collect(Collectors.toList());
//        }
//        for (ChatMessage chatMessage : joined) {
//            log.info("joined = {}", chatMessage.getRole());
//            log.info("joined = {}", chatMessage.getContent());
//
//        }
//
//        String requestId = sendConversationMessage(request, joined);
//        if (CollectionUtils.isEmpty(messages)) {
//            return ResponseModel.fail("messages can not be empty");
//        }
//        String responseMessage = responseConversationMessage(request, messages, requestId);
//        return ResponseModel.success(responseMessage);
//    }
//
//    private String sendConversationMessage(HttpServletRequest request, List<ChatMessage> messages) {
//        String gptMessage = messages.toString();
////        if (!MapUtils.isEmpty(stockConversation)) {
////            for (Integer key : stockConversation.keySet()) {
////                String value = stockConversation.get(key).toString();
////                temp += value;
////            }
////            gptMessage = temp + gptMessage;
////        }
////        stockConversation.put(count++, messages); //사용자의 요청 값 저장
//        if (!stock.isEmpty()) {
//            for (List<ChatMessage> chatMessages : stock) {
//                String str = chatMessages.toString();
//                temp += str;
//            }
//            gptMessage += temp;
//        }
//        stock.add(messages);
////        String cleanedMessage = gptMessage.replaceAll("\\[|\\]", "");
////        String message = "[" + cleanedMessage + "]";
////        log.info("message = {}", message);
//
////        // 정규식 패턴을 사용하여 ChatMessage를 찾습니다.
////        String pattern = "(ChatMessage\\(.*?\\))";
////
////        // 정규식 패턴과 매치되는 모든 문자열을 찾습니다.
////        Pattern regex = Pattern.compile(pattern);
////        Matcher matcher = regex.matcher(message);
////
////        // 첫 번째 ChatMessage는 그대로 두고 이미 존재하는 ", "가 있는 ChatMessage는 제외하고
////        // 나머지 ChatMessage 앞에 ", "를 추가하여 결과를 생성합니다.
////        StringBuilder result = new StringBuilder();
////        if (matcher.find()) {
////            result.append(matcher.group()).append("\n");
////        }
////        boolean firstMatch = true;
////        while (matcher.find()) {
////            if (matcher.group().contains(", ")) {
////                result.append(matcher.group()).append("\n");
////            } else {
////                if (firstMatch) {
////                    firstMatch = false;
////                } else {
////                    result.append(", ");
////                }
////                result.append(matcher.group()).append("\n");
////            }
////        }
//
//        String requestId = UUID.randomUUID().toString();
//        log.info("requestId {}, ip {}, send messages : {}", requestId, request.getRemoteHost(), gptMessage);
//        return requestId;
//    }
//
//    private String responseConversationMessage(HttpServletRequest request, List<ChatMessage> messages, String requestId) {
//        ChatMessage response = gptService.Chat(messages);
//
////        stockConversation.put(count++, Collections.singletonList(response)); //gpt 응답 값 저장
//        stock.add(Collections.singletonList(response));
//
//        log.info("requestId {}, ip {}\n get a reply : {}", requestId, request.getRemoteHost(), response);
//        return response.getContent();
//    }
//
//    private String sendMessageFirst(HttpServletRequest request, List<ChatMessage> messages) {
//        String gptMessage = messages.toString();
//        String requestId = UUID.randomUUID().toString();
//        log.info("requestId {}, ip {}, send messages : {}", requestId, request.getRemoteHost(), gptMessage);
//        return requestId;
//    }
//
//    private String responseMessageFirst(HttpServletRequest request, List<ChatMessage> messages, String requestId) {
//        ChatMessage response = gptService.Chat(messages);
//        String responseMessage = makeJson(response); //1번째 responseMessage() 메서드 실행시 음식 3가지 추천받음
//        log.info("requestId {}, ip {}\n get a reply : {}", requestId, request.getRemoteHost(), responseMessage);
//        return responseMessage;
//    }
//
//    private String makeJson(ChatMessage response) {
//        String responseMessage = response.getContent().toString();
//        Gson gson = new Gson();
//        RecipeDataJson recipeData = gson.fromJson(responseMessage, RecipeDataJson.class);
//
//        recommendFood(recipeData);
//        return responseMessage;
//    }
//
//    public Result recommendFood(RecipeDataJson recipeData) {
//        log.info("recommendFood 메서드 호출 성공");
//        return new Result(recipeData);
//    }
//
//    @Data
//    @AllArgsConstructor
//    static class Result<T> {
//        private T recipe;
//    }
//}