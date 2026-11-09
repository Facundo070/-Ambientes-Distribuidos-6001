import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Alumno } from './alumno.entity.js';

@Injectable()
export class AlumnosService {
  constructor(
    @InjectRepository(Alumno)
    private alumnoRepository: Repository<Alumno>,
  ) {}

  async create(nombre: string) {
    return this.alumnoRepository.save({ nombre });
  }

  async findAll() {
    return this.alumnoRepository.find();
  }
  async update(id: number, nombre: string) {
  await this.alumnoRepository.update(id, { nombre });
  return this.alumnoRepository.findOneBy({ id });
}

async remove(id: number) {
  return this.alumnoRepository.delete(id);
}
}