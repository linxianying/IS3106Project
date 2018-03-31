package jsf.managedBean;

import entity.Lecturer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "utilManagedBean")
@RequestScoped

public class utilManagedBean {

    public utilManagedBean() {
    }

    public String formatLecturer(List<Lecturer> lecturers) {
        String format = "";
        for (int i = 0; i < lecturers.size(); i++) {
            format = lecturers.get(i).getName() + ", ";
        }
        return format;
    }
    
    
    public String formatDate(Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }
    
    public String formatMotdHeader(Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
        return simpleDateFormat.format(date);
    }
}
