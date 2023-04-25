DELIMITER $$
DROP FUNCTION IF EXISTS EmployeeCategory $$
CREATE FUNCTION EmployeeCategory(
    salary DECIMAL(10,2)
)
    RETURNS VARCHAR(20)
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE employeeCategory VARCHAR(20);

    IF salary < 2000 THEN
        SET employeeCategory = 'JUNIOR';
    ELSEIF (salary >= 2000 AND salary <= 3000) THEN
        SET employeeCategory = 'SENIOR';
    ELSEIF salary > 3000 THEN
        SET employeeCategory = 'ANALYST';
    END IF;

    -- RETURN (employeeCategory);
    RETURN employeeCategory;
END$$
DELIMITER ;

select email, salary, EmployeeCategory(salary) from employee e;