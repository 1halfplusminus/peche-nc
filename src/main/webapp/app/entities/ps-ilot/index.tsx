import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PsIlot from './ps-ilot';
import PsIlotDetail from './ps-ilot-detail';
import PsIlotUpdate from './ps-ilot-update';
import PsIlotDeleteDialog from './ps-ilot-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PsIlotUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PsIlotUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PsIlotDetail} />
      <ErrorBoundaryRoute path={match.url} component={PsIlot} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PsIlotDeleteDialog} />
  </>
);

export default Routes;
