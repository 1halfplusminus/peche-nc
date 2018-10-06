export const enum PSStatus {
  VALIDE = 'VALIDE',
  NONVALIDE = 'NONVALIDE'
}

export interface IPsIlot {
  id?: number;
  lat?: number;
  lng?: number;
  idIlot?: string;
  calendrier?: string;
  commune?: string;
  posehelico?: string;
  titre?: string;
  copyright?: string;
  photoContentType?: string;
  photo?: any;
  status?: PSStatus;
}

export const defaultValue: Readonly<IPsIlot> = {};
