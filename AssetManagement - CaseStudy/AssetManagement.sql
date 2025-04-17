create database AssetManagement;
use Assetmanagement;



create table Employees(
    Employee_id INT PRIMARY KEY AUTO_INCREMENT,
    Employee_name varchar(30) NOT NULL,
    Department ENUM("IT","SALES","HR","MARKETING") NOT NULL,
    Email varchar(50) UNIQUE NOT NULL,
    Password VARCHAR(12) NOT NULL
);

create table Assets(
    Asset_id INT PRIMARY KEY AUTO_INCREMENT,
    Asset_name varchar(40) NOT NULL,
    Asset_type ENUM("Laptop","Mobile","Vehicle","Equipment") NOT NULL,
    Serial_number VARCHAR(100) UNIQUE NOT NULL,
    Purchase_date DATE NOT NULL,
    Location VARCHAR(100) NOT NULL,
    Status VARCHAR(20) NOT NULL DEFAULT "In use",
    CHECK(Status IN ("In Use","Decommissioned","Under Maintenance")),
    Employee_id INT,
    FOREIGN KEY(Employee_id) REFERENCES Employees(Employee_id) ON DELETE SET NULL
);

create table MaintainanceRecords(
    Maintainance_id INT PRIMARY KEY AUTO_INCREMENT,
    Asset_id INT,
    Maintainace_dat DATE NOT NULL,
    Description varchar(100) NOT NULL,
    Cost Decimal(10,3) DEFAULT 0.00,
    FOREIGN KEY(Asset_id) REFERENCES Assets(Asset_id) ON DELETE CASCADE
);

create table AssetAllocations(
    Allocation_id INT PRIMARY KEY AUTO_INCREMENT,
    Asset_id INT,
    Employee_id INT,
    Allocation_date DATE NOT NULL,
    Return_Date DATE DEFAULT NULL,
    FOREIGN KEY(Asset_id) REFERENCES Assets(Asset_id),
    FOREIGN KEY(Employee_id) REFERENCES Employees(Employee_id)
);

create table Reservations(
    Reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    Asset_id INT,
    Employee_id INT,
    Reservation_date DATE NOT NULL,
    Start_date DATE NOT NULL,
    End_date DATE NOT NULL,
    Status VARCHAR(20) NOT NULL DEFAULT "Pending",
    CHECK(STATUS IN("Pending","Approved","Canceled")),
    FOREIGN KEY(Asset_id) REFERENCES Assets(Asset_id) ON DELETE CASCADE,
    FOREIGN KEY (Employee_id) REFERENCES Employees(Employee_id) ON DELETE CASCADE
);

INSERT INTO Employees (Employee_name, Department, Email, Password) VALUES
('Arun Kumar', 'IT', 'arun.kumar@example.com', 'Pass@1234'),
('Divya Lakshmi', 'HR', 'divya.lakshmi@example.com', 'Divya@2024'),
('Karthik R', 'SALES', 'karthik.r@example.com', 'Karthik#88'),
('Sneha Priya', 'MARKETING', 'sneha.priya@example.com', 'Sneha@321'),
('Ravi Shankar', 'IT', 'ravi.shankar@example.com', 'Ravi@1234'),
('Meena Kumari', 'SALES', 'meena.kumari@example.com', 'Meena#777'),
('Vignesh S', 'HR', 'vignesh.s@example.com', 'Vignesh@456'),
('Anitha B', 'MARKETING', 'anitha.b@example.com', 'Ani@1234'),
('Suresh Nair', 'IT', 'suresh.nair@example.com', 'Suresh#99'),
('Lakshmi Narayan', 'HR', 'lakshmi.narayan@example.com', 'Lakshmi@2025');

INSERT INTO Assets (Asset_name, Asset_type, Serial_number, Purchase_date, Location, Status, Employee_id) VALUES
('Dell XPS 15', 'Laptop', 'DLXPS001', '2022-01-15', 'Chennai', 'In Use', 1),
('iPhone 13', 'Mobile', 'IPH013002', '2023-03-12', 'Bangalore', 'In Use', 2),
('Honda Activa', 'Vehicle', 'ACTV3003', '2021-07-22', 'Hyderabad', 'Under Maintenance', 3),
('Canon DSLR', 'Equipment', 'CNDLR004', '2020-11-01', 'Kochi', 'Decommissioned', 4),
('HP EliteBook', 'Laptop', 'HPEL005', '2022-09-10', 'Chennai', 'In Use', 5),
('Samsung Galaxy S21', 'Mobile', 'SGS21006', '2023-06-18', 'Bangalore', 'In Use', 6),
('Tata Tiago', 'Vehicle', 'TTG007', '2022-12-01', 'Chennai', 'In Use', 7),
('Epson Printer', 'Equipment', 'EPS008', '2021-04-15', 'Hyderabad', 'Under Maintenance', 3),
('Lenovo ThinkPad', 'Laptop', 'LTP009', '2023-01-30', 'Kochi', 'In Use', 2),
('Redmi Note 11', 'Mobile', 'RNM0110', '2023-10-05', 'Chennai', 'In Use', 1);

INSERT INTO MaintainanceRecords (Asset_id, Maintainace_dat, Description, Cost) VALUES
(3, '2024-01-05', 'Engine oil change and general checkup', 1500.00),
(4, '2023-12-10', 'Camera lens replacement', 2500.50),
(8, '2024-02-18', 'Ink system flush and cleaning', 600.75),
(1, '2024-03-01', 'Battery replacement', 2000.00),
(7, '2023-08-25', 'Brake repair', 1800.90),
(5, '2024-01-20', 'Keyboard and touchpad service', 1200.00),
(2, '2024-04-10', 'Screen replacement', 3200.00),
(9, '2023-11-05', 'SSD upgrade', 3000.00),
(6, '2023-09-15', 'Software update and diagnostics', 950.00),
(10, '2024-03-25', 'Screen replacement', 2800.00);

INSERT INTO AssetAllocations (Asset_id, Employee_id, Allocation_date, Return_Date) VALUES
(1, 1, '2024-04-01', '2025-03-30'),
(2, 2, '2023-05-10', '2024-05-09'),
(3, 4, '2024-01-05', NULL),
(4, 5, '2024-06-15', '2025-06-14'),
(5, 6, '2022-09-12', '2023-09-11'),
(7, 8, '2023-11-20', NULL),
(8, 9, '2024-02-10', '2025-02-09'),
(9, 10, '2024-04-01', NULL),
(6, 3, '2021-12-01', '2022-11-30'),
(10, 3, '2023-04-10', '2024-04-09');

INSERT INTO Reservations (Asset_id, Employee_id, Reservation_date, Start_date, End_date, Status) VALUES
(1, 2, '2024-04-05', '2024-04-10', '2024-04-15', 'Approved'),
(2, 3, '2024-04-06', '2024-04-12', '2024-04-16', 'Pending'),
(4, 5, '2024-04-07', '2024-04-13', '2024-04-17', 'Canceled'),
(5, 6, '2024-04-08', '2024-04-14', '2024-04-18', 'Approved'),
(6, 1, '2024-04-09', '2024-04-15', '2024-04-19', 'Pending'),
(7, 8, '2024-04-10', '2024-04-16', '2024-04-20', 'Canceled'),
(8, 9, '2024-04-11', '2024-04-17', '2024-04-21', 'Approved'),
(9, 10, '2024-04-12', '2024-04-18', '2024-04-22', 'Pending'),
(10, 3, '2024-04-13', '2024-04-19', '2024-04-23', 'Approved'),
(3, 4, '2024-04-14', '2024-04-20', '2024-04-24', 'Approved');

select * from employees;
select * from Assets;
select * from MaintainanceRecords;
select * from AssetAllocations;
select * from reservations;
