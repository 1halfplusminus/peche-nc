import { IPsMarina } from 'app/shared/model//ps-marina.model';

export const enum PSStatus {
  VALIDE = 'VALIDE',
  NONVALIDE = 'NONVALIDE'
}

export interface IPsMiseAEau {
  id?: number;
  lat?: number;
  lng?: number;
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
  marinas?: IPsMarina;
}

export const defaultValue: Readonly<IPsMiseAEau> = {
  amenagee: false,
  parking: false,
  payant: false,
  pointEau: false,
  poubelles: false,
  toilettes: false
};
