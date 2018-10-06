import { Moment } from 'moment';

export interface IMarkers {
  id?: number;
  iconContentType?: string;
  icon?: any;
  titre?: string;
  description?: any;
  email?: string;
  pjContentType?: string;
  pj?: any;
  ip?: string;
  lat?: number;
  lng?: number;
  date?: Moment;
}

export const defaultValue: Readonly<IMarkers> = {};
