package chatserver.entity;

//@Data
//@Entity
//@Table(name = "contact")
//@SecondaryTables(
//        @SecondaryTable(name = "user")
//)
public class ContactDetail {
    private int userType; // 0: human , 1: bot
    private String nickName;
    private String categoryName;
    private int gender; // 0: unknown, 1: male, 2: female
    private String tags;
    private String description;
    private String headIconUrl;
}
