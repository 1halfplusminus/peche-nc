import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { openFile, byteSize, Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './markers.reducer';
import { IMarkers } from 'app/shared/model/markers.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMarkersProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IMarkersState {
  search: string;
}

export class Markers extends React.Component<IMarkersProps, IMarkersState> {
  state: IMarkersState = {
    search: ''
  };

  componentDidMount() {
    this.props.getEntities();
  }

  search = () => {
    if (this.state.search) {
      this.props.getSearchEntities(this.state.search);
    }
  };

  clear = () => {
    this.props.getEntities();
    this.setState({
      search: ''
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  render() {
    const { markersList, match } = this.props;
    return (
      <div>
        <h2 id="markers-heading">
          <Translate contentKey="pechencApp.markers.home.title">Markers</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="pechencApp.markers.home.createLabel">Create new Markers</Translate>
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('pechencApp.markers.home.search')}
                  />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.markers.icon">Icon</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.markers.titre">Titre</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.markers.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.markers.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.markers.pj">Pj</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.markers.ip">Ip</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.markers.lat">Lat</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.markers.lng">Lng</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.markers.date">Date</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {markersList.map((markers, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${markers.id}`} color="link" size="sm">
                      {markers.id}
                    </Button>
                  </td>
                  <td>
                    {markers.icon ? (
                      <div>
                        <a onClick={openFile(markers.iconContentType, markers.icon)}>
                          <img src={`data:${markers.iconContentType};base64,${markers.icon}`} style={{ maxHeight: '30px' }} />
                          &nbsp;
                        </a>
                        <span>
                          {markers.iconContentType}, {byteSize(markers.icon)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{markers.titre}</td>
                  <td>{markers.description}</td>
                  <td>{markers.email}</td>
                  <td>
                    {markers.pj ? (
                      <div>
                        <a onClick={openFile(markers.pjContentType, markers.pj)}>
                          <img src={`data:${markers.pjContentType};base64,${markers.pj}`} style={{ maxHeight: '30px' }} />
                          &nbsp;
                        </a>
                        <span>
                          {markers.pjContentType}, {byteSize(markers.pj)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{markers.ip}</td>
                  <td>{markers.lat}</td>
                  <td>{markers.lng}</td>
                  <td>
                    <TextFormat type="date" value={markers.date} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${markers.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${markers.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${markers.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ markers }: IRootState) => ({
  markersList: markers.entities
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Markers);
