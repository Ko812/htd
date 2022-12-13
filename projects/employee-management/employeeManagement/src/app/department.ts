import { Employee } from "./employee";

export interface Department {
    departmentCode: number;
    departmentName: string;
    employees: Employee[];

    toString(): string;

}
