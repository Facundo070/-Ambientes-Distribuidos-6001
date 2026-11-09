import { Controller,Get,Post,Put,Delete,Param,Body, } from '@nestjs/common';
import { AlumnosService } from './alumnos.service.js';

@Controller('alumnos')
export class AlumnosController {
  constructor(
    private readonly alumnosService: AlumnosService,
  ) {}

  @Post()
  create(
    @Body() body: { nombre: string },
  ) {
    return this.alumnosService.create(body.nombre);
  }

  @Get()
  findAll() {
    return this.alumnosService.findAll();
  }
  @Put(':id')
update(
  @Param('id') id: number,
  @Body() body: { nombre: string },
) {
  return this.alumnosService.update(
    Number(id),
    body.nombre,
  );
}

@Delete(':id')
remove(
  @Param('id') id: number,
) {
  return this.alumnosService.remove(
    Number(id),
  );
}
}