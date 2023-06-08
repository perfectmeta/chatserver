package prompteditor.dto;


import lombok.Data;

@Data
public class UserCategoryPOJO {
    private long userCategoryId;
    private int userType; // 0: human , 1: bot
    private String userCategoryName;
    private int gender; // 0: unknown, 1: male, 2: female
    private String tags;
    private String description;
    private String prompt;
}
