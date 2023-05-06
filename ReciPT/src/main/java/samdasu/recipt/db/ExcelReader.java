//package samdasu.recipt.db;
//
//import org.apache.poi.ss.usermodel.*;
//import samdasu.recipt.entity.DbRecipe;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//public class ExcelReader {
//
//    //엑셀 파일 읽어서 각 컬럼에 맞게 데이터 저장한 레시피 리스트 반환
//    public static List<DbRecipe> readRecipesFromExcel(File file) throws IOException {
//        List<DbRecipe> recipes = new ArrayList<>();
//        try (Workbook workbook = WorkbookFactory.create(file)) {
//            Sheet sheet = workbook.getSheetAt(0);
//            Iterator<Row> rowIterator = sheet.rowIterator();
//            rowIterator.next(); // 컬럼명 스킵
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                String dbFoodName = getStringCellValue(row.getCell(0));
//                String dbIngredient = getStringCellValue(row.getCell(1));
//                String howToCook = getStringCellValue(row.getCell(2));
//                String thumbnailImage = getStringCellValue(row.getCell(3));
//                String dbContext = getStringCellValue(row.getCell(4));
//                String dbImage = getStringCellValue(row.getCell(5));
//                Integer dbLikeCount = getIntegerCellValue(row.getCell(6));
//                Long dbViewCount = getLongCellValue(row.getCell(7));
//                Double dbRatingScore = getDoubleCellValue(row.getCell(8));
//                Integer dbRatingPeople = getIntegerCellValue(row.getCell(9));
//                Allergy allergy = new Allergy("", "");
//                DbRecipe recipe = DbRecipe.createDbRecipe(dbFoodName, dbIngredient, howToCook, thumbnailImage, dbContext, dbImage, dbLikeCount, dbViewCount, dbRatingScore, dbRatingPeople, allergy);
//                recipes.add(recipe);
//            }
//        }
//        return recipes;
//    }
//
//    private static String getStringCellValue(Cell cell) {
//        if (cell == null) {
//            return "";
//        }
//        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : "";
//    }
//
//    private static Integer getIntegerCellValue(Cell cell) {
//        if (cell == null) {
//            return 0;
//        }
//        return cell.getCellType() == CellType.NUMERIC ? (int) cell.getNumericCellValue() : 0;
//    }
//
//    private static Long getLongCellValue(Cell cell) {
//        if (cell == null) {
//            return 0L;
//        }
//        return cell.getCellType() == CellType.NUMERIC ? (long) cell.getNumericCellValue() : 0L;
//    }
//
//    private static Double getDoubleCellValue(Cell cell) {
//        if (cell == null) {
//            return 0.0;
//        }
//        return cell.getCellType() == CellType.NUMERIC ? cell.getNumericCellValue() : 0.0;
//    }
//}
