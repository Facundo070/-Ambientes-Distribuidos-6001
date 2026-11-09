import { Entity, PrimaryGeneratedColumn, Column } from 'typeorm';

@Entity()
export class Alumno {

  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  nombre: string;
}