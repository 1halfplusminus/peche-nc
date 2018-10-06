import { Moment } from 'moment';

export const enum DCPEtat {
  DISPARU = 'DISPARU',
  NORMAL = 'NORMAL'
}

export interface IDCP {
  id?: number;
  nom?: string;
  position?: string;
  lat?: number;
  lng?: number;
  dateDerniereMaj?: Moment;
  etat?: DCPEtat;
  localisation?: string;
}

export const defaultValue: Readonly<IDCP> = {};
