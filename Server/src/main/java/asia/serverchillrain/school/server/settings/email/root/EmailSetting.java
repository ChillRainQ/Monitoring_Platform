package asia.serverchillrain.school.server.settings.email.root;

import asia.serverchillrain.school.server.settings.Setting;
import asia.serverchillrain.school.server.settings.email.EmailContent;
import asia.serverchillrain.school.server.settings.email.EmailSystemUser;
import asia.serverchillrain.school.server.settings.email.EmailTime;
import asia.serverchillrain.school.server.settings.email.EmailTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * &#064;auther  2024 02 02
 * 邮箱设置
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSetting extends Setting {
    private EmailContent email_content;
    private EmailSystemUser system_email;
    private EmailTime email_time;
    private EmailTitle email_title;

    @Override
    public String getApiByType(String type) {
        if("email_content".equals(type)) return email_content.getLine();
        if("system_email".equals(type)) return system_email.getLine();
        if("email_time".equals(type)) return email_time.getLine();
        if("email_title".equals(type)) return email_title.getLine();
        return null;
    }
}
