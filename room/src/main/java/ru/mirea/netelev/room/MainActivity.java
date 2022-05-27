package ru.mirea.netelev.room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase db = App.getInstance().getDatabase();
        EmployeeDao employeeDao = db.employeeDao();
        Employee employee = new Employee();
        employee.id = 2;
        employee.name = "John Smith";
        employee.salary = 12000;
// запись сотрудников в базу
        employeeDao.insert(employee);
// Загрузка всех работников
        List<Employee> employees = employeeDao.getAll();
// Получение определенного работника с id = 1
        employee = employeeDao.getById(2);
// Обновление полей объекта
        employee.salary = 20000;
        employeeDao.update(employee);
        Log.i(TAG, employee.name + " " + employee.salary);
    }

    @Entity
    public static class Employee {
        @PrimaryKey(autoGenerate = true)
        public long id;
        public String name;
        public int salary;
    }
    @Dao
    public interface EmployeeDao {
        @Query("SELECT * FROM employee")
        List<Employee> getAll();
        @Query("SELECT * FROM employee WHERE id = :id")
        Employee getById(long id);
        @Insert
        void insert(Employee employee);
        @Update
        void update(Employee employee);
        @Delete
        void delete(Employee employee);
    }

    @Database(entities = {Employee.class}, version = 1)
    public abstract static class AppDatabase extends RoomDatabase {
        public abstract EmployeeDao employeeDao();
    }
}