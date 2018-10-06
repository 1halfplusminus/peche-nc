import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Markers from './markers';
import MarkersDetail from './markers-detail';
import MarkersUpdate from './markers-update';
import MarkersDeleteDialog from './markers-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MarkersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MarkersUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MarkersDetail} />
      <ErrorBoundaryRoute path={match.url} component={Markers} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MarkersDeleteDialog} />
  </>
);

export default Routes;
