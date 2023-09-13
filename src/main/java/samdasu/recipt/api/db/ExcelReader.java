package samdasu.recipt.api.db;

import org.apache.poi.ss.usermodel.*;
import samdasu.recipt.domain.entity.Recipe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    //엑셀 파일 읽어서 각 컬럼에 맞게 데이터 저장한 레시피 리스트 반환
    public static List<Recipe> readRecipesFromExcel(File file) throws IOException {
        List<Recipe> recipes = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            rowIterator.next(); // 컬럼명 스킵
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String foodName = getStringCellValue(row.getCell(0));
                String ingredient = getStringCellValue(row.getCell(1));
                String category = getStringCellValue(row.getCell(2));
                String thumbnailImage = getStringCellValue(row.getCell(3));
                String context = getStringCellValue(row.getCell(4));
                String image = getStringCellValue(row.getCell(5));
                Long viewCount = getLongCellValue(row.getCell(7));
                Integer likeCount = getIntegerCellValue(row.getCell(6));
                Double ratingScore = getDoubleCellValue(row.getCell(8));
                Integer ratingPeople = getIntegerCellValue(row.getCell(9));
                Recipe recipe = Recipe.createRecipe(foodName, ingredient, category, thumbnailImage, context, image, viewCount, likeCount, ratingScore, ratingPeople);
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    private static String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : "";
    }

    private static Integer getIntegerCellValue(Cell cell) {
        if (cell == null) {
            return 0;
        }
        return cell.getCellType() == CellType.NUMERIC ? (int) cell.getNumericCellValue() : 0;
    }

    private static Long getLongCellValue(Cell cell) {
        if (cell == null) {
            return 0L;
        }
        return cell.getCellType() == CellType.NUMERIC ? (long) cell.getNumericCellValue() : 0L;
    }

    private static Double getDoubleCellValue(Cell cell) {
        if (cell == null) {
            return 0.0;
        }
        return cell.getCellType() == CellType.NUMERIC ? cell.getNumericCellValue() : 0.0;
    }
}
