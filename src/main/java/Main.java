import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    
    private static boolean isValidSchedule(String schedule) {
        if (schedule.contains("N, N")) {
            return false;
        }

        if (schedule.contains("T, T, T, T")) {
            return false;
        }

        int countN = schedule.length() - schedule.replace("N", "").length();
        if (countN != 2) {
            return false;
        }

        return schedule.chars().filter(ch -> ch == 'T').count() == 5;
    }

    private static String generateRandomSchedule() {
        StringBuilder schedule = new StringBuilder();

        Random random = new Random();
        int countT = 0;
        int countN = 0;
        for (int i = 0; i < 7; i++) {
            if (countT == 5) {
                schedule.append("N, ");
                countN++;
            } else if (countN == 2) {
                schedule.append("T, ");
                countT++;
            } else {
                char randomDay = random.nextBoolean() ? 'T' : 'N';
                schedule.append(randomDay).append(", ");
                if (randomDay == 'T') {
                    countT++;
                } else {
                    countN++;
                }
            }
        }

        String scheduleStr = schedule.substring(0, schedule.length() - 2);
        while (!isValidSchedule(scheduleStr)) {
            schedule = new StringBuilder();
            countT = 0;
            countN = 0;
            for (int i = 0; i < 7; i++) {
                if (countT == 5) {
                    schedule.append("N, ");
                    countN++;
                } else if (countN == 2) {
                    schedule.append("T, ");
                    countT++;
                } else {
                    char randomDay = random.nextBoolean() ? 'T' : 'N';
                    schedule.append(randomDay).append(", ");
                    if (randomDay == 'T') {
                        countT++;
                    } else {
                        countN++;
                    }
                }
            }
            scheduleStr = schedule.substring(0, schedule.length() - 2);
        }

        return scheduleStr;
    }

    public static void main(String[] args) {
        File logFile = new File("log.txt");
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String lastSchedule;
        if (!lines.isEmpty()) {
            lastSchedule = lines.get(lines.size() - 1);
        } else {
            lastSchedule = generateRandomSchedule();
            try (PrintWriter writer = new PrintWriter(new FileWriter(logFile))) {
                writer.println(lastSchedule);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String newSchedule = generateRandomSchedule();
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
            if (lines.size() == 1) {
                writer.print(newSchedule);
            }
            else {
                writer.println();
                writer.print(newSchedule);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Last Week's Schedule: \n" + "S, M, T, W, Th, F, Sa\n" + "---------------------\n" + lastSchedule);
        System.out.println("\nThis Week's Schedule: \n" + "S, M, T, W, Th, F, Sa\n" + "---------------------\n" + newSchedule);
    }
    
}