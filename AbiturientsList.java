//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package webapp1;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/AbiturientsList"})
public class AbiturientsList extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ArrayList<Abiturient> abiturients;


    public AbiturientsList() throws IOException {
        this.abiturients = new ArrayList<>();


        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream("C:\\Users\\vinog\\IdeaProjects\\lab2_v2\\src\\webapp1\\data.json"), "UTF8"));

        //FileReader file = new FileReader("C:\\Users\\vinog\\IdeaProjects\\lab2_v2\\src\\webapp1\\data.json");
        JsonObject json = new Gson().fromJson(in, JsonObject.class);
        System.out.println(json);

        JsonArray abiturientsJson = json.getAsJsonArray("abiturients");
        for (int i = 0; i < abiturientsJson.size(); i++) {
            Abiturient abiturient = new Gson().fromJson(abiturientsJson.get(i), Abiturient.class);
            this.abiturients.add(abiturient);
        }


        //abiturients.add(new Abiturient(0, "Иванов", "Иван", "Иванович", "ФКТИ"));
        //abiturients.add(new Abiturient(1, "Петров", "Петр", "Петрович", "ФКТИ"));
        //abiturients.add(new Abiturient(2, "Сидоров", "Игорь", "Сергеевич", "ФРТ"));
        //abiturients.add(new Abiturient(3, "Мышкин", "Евгений", "Анатольевич", "ОФ"));
        //abiturients.add(new Abiturient(4, "Птичкин", "Федор", "Павлович", "ОФ"));
        //abiturients.add(new Abiturient(5, "Кошкин", "Сергей", "Константинович", "ОФ"));
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String filter = request.getParameter("filter");

        System.out.println(this.abiturients.size());
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            out.println("<html>");
            out.println("<head><title>Список абитуриентов</title></head>");
            out.println("<body>");
            out.println("<form method='GET' action=AbiturientsList>");
            out.println("<p>фильтр по факультетам</p>");
            out.println("<input type='text' name='filter'/>");
            out.println("<button type='submit'>Применить</button>");
            out.println("</form>");
            out.println("<h1>Список абитуриентов на факультет " + (filter != null ? filter : "все факультеты") + "</h1>");
            out.println("<table border='1'>");
            out.println("<tr><td><b>ФИО</b></td><td><b>Факультет </b></td>");
            for (Abiturient abiturient : this.abiturients) {
                String fio = abiturient.getFio();
                String faculty = abiturient.getFaculty();
                if (filter != null && filter != "") {
                        if (filter.equals(faculty))
                            out.println("<tr><td>" + fio + "</td><td>" + faculty + "</td></tr>");
                    } else out.println("<tr><td>" + fio + "</td><td>" + faculty + "</td></tr>");
                }

            out.println("</table>");
            out.println("<form method='POST' action=AbiturientsList?save=true>");
            out.println("<p>Добавить абитуриента</p>");
            out.println("<span>Фамилия</span>");
            out.println("<input type='text' name='lastName'/>");
            out.println("<span>Имя</span>");
            out.println("<input type='text' name='firstName'/>");
            out.println("<span>Отчество</span>");
            out.println("<input type='text' name='middleName'/>");
            out.println("<span>Факультет</span>");
            out.println("<input type='text' name='faculty'/>");
            out.println("<button type='submit'>Сохранить</button>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        if (request.getParameter("save") != null && request.getParameter("save") != "") {
            Integer newId = this.abiturients.size();
            String lastName = request.getParameter("lastName");
            String firstName = request.getParameter("firstName");
            String middleName = request.getParameter("middleName");
            String faculty = request.getParameter("faculty");

            this.abiturients.add(new Abiturient(newId, lastName, firstName, middleName, faculty));
            //Writer writer = new FileWriter("C:\\Users\\vinog\\IdeaProjects\\lab2_v2\\src\\webapp1\\data.json");
            Writer writer = new OutputStreamWriter(new FileOutputStream("C:\\Users\\vinog\\IdeaProjects\\lab2_v2\\src\\webapp1\\data.json"), "UTF8");
            try {
                Gson gson = new Gson();
                JsonObject json = new JsonObject();
                json.add("abiturients", gson.toJsonTree(this.abiturients));
                System.out.println();
                gson.toJson(json, writer);
            }
            finally {
                writer.close();
            }
        }
        this.processRequest(request, response);
    }
}
