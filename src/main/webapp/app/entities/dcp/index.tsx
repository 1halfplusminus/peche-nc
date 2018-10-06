import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DCP from './dcp';
import DCPDetail from './dcp-detail';
import DCPUpdate from './dcp-update';
import DCPDeleteDialog from './dcp-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DCPUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DCPUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DCPDetail} />
      <ErrorBoundaryRoute path={match.url} component={DCP} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DCPDeleteDialog} />
  </>
);

export default Routes;
