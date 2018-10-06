import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import dCP, {
  DCPState
} from 'app/entities/dcp/dcp.reducer';
// prettier-ignore
import markers, {
  MarkersState
} from 'app/entities/markers/markers.reducer';
// prettier-ignore
import psIlot, {
  PsIlotState
} from 'app/entities/ps-ilot/ps-ilot.reducer';
// prettier-ignore
import psMarina, {
  PsMarinaState
} from 'app/entities/ps-marina/ps-marina.reducer';
// prettier-ignore
import psMiseAEau, {
  PsMiseAEauState
} from 'app/entities/ps-mise-a-eau/ps-mise-a-eau.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly dCP: DCPState;
  readonly markers: MarkersState;
  readonly psIlot: PsIlotState;
  readonly psMarina: PsMarinaState;
  readonly psMiseAEau: PsMiseAEauState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  dCP,
  markers,
  psIlot,
  psMarina,
  psMiseAEau,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
