import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PsMiseAEau from './ps-mise-a-eau';
import PsMiseAEauDetail from './ps-mise-a-eau-detail';
import PsMiseAEauUpdate from './ps-mise-a-eau-update';
import PsMiseAEauDeleteDialog from './ps-mise-a-eau-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PsMiseAEauUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PsMiseAEauUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PsMiseAEauDetail} />
      <ErrorBoundaryRoute path={match.url} component={PsMiseAEau} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PsMiseAEauDeleteDialog} />
  </>
);

export default Routes;
