import * as mime from "mime";
import { Department } from "./department";
import { Project } from "./project";

export interface Employee {
    id: number;
    name: string;
    role: string;
    department: Department | undefined;
    project: Project | undefined;
    createdBy: string;
    creatorId: number;
    creationDate: Date;
    updatedBy: String | undefined | null;
    updaterId: Number | undefined | null;
    dateOfUpdate: Date | undefined | null;
    getName(): string;
}
