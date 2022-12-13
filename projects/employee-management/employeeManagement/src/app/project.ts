import { Employee } from "./employee";

export interface Project {
    projectCode: number;
    projectTitle: string;
    employees: Employee[];
    createdBy: string;
    creatorId: number;
}
