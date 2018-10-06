import { IPsMiseAEau } from 'app/shared/model//ps-mise-a-eau.model';

export const enum PSStatus {
  VALIDE = 'VALIDE',
  NONVALIDE = 'NONVALIDE'
}

export interface IPsMarina {
  id?: number;
  lat?: number;
  lng?: number;
  idMarina?: string;
  access?: string;
  amenagee?: boolean;
  commune?: string;
  lieuDit?: string;
  parkingPlace?: number;
  observation?: string;
  parking?: boolean;
  payant?: boolean;
  pointEau?: boolean;
  poubelles?: boolean;
  toilettes?: boolean;
  titre?: string;
  photosContentType?: string;
  photos?: any;
  status?: PSStatus;
  miseAEaux?: IPsMiseAEau[];
}

export const defaultValue: Readonly<IPsMarina> = {
  amenagee: false,
  parking: false,
  payant: false,
  pointEau: false,
  poubelles: false,
  toilettes: false
};
