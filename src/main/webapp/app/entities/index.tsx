import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DCP from './dcp';
import Markers from './markers';
import PsIlot from './ps-ilot';
import PsMarina from './ps-marina';
import PsMiseAEau from './ps-mise-a-eau';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/dcp`} component={DCP} />
      <ErrorBoundaryRoute path={`${match.url}/markers`} component={Markers} />
      <ErrorBoundaryRoute path={`${match.url}/ps-ilot`} component={PsIlot} />
      <ErrorBoundaryRoute path={`${match.url}/ps-marina`} component={PsMarina} />
      <ErrorBoundaryRoute path={`${match.url}/ps-mise-a-eau`} component={PsMiseAEau} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
