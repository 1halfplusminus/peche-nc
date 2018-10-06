import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PsMarina from './ps-marina';
import PsMarinaDetail from './ps-marina-detail';
import PsMarinaUpdate from './ps-marina-update';
import PsMarinaDeleteDialog from './ps-marina-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PsMarinaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PsMarinaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PsMarinaDetail} />
      <ErrorBoundaryRoute path={match.url} component={PsMarina} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PsMarinaDeleteDialog} />
  </>
);

export default Routes;
