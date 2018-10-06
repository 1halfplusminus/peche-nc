import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { openFile, byteSize, Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './ps-mise-a-eau.reducer';
import { IPsMiseAEau } from 'app/shared/model/ps-mise-a-eau.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPsMiseAEauProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IPsMiseAEauState {
  search: string;
}

export class PsMiseAEau extends React.Component<IPsMiseAEauProps, IPsMiseAEauState> {
  state: IPsMiseAEauState = {
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
    const { psMiseAEauList, match } = this.props;
    return (
      <div>
        <h2 id="ps-mise-a-eau-heading">
          <Translate contentKey="pechencApp.psMiseAEau.home.title">Ps Mise A Eaus</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="pechencApp.psMiseAEau.home.createLabel">Create new Ps Mise A Eau</Translate>
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
                    placeholder={translate('pechencApp.psMiseAEau.home.search')}
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
                  <Translate contentKey="pechencApp.psMiseAEau.lat">Lat</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.lng">Lng</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.access">Access</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.amenagee">Amenagee</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.commune">Commune</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.lieuDit">Lieu Dit</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.parkingPlace">Parking Place</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.observation">Observation</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.parking">Parking</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.payant">Payant</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.pointEau">Point Eau</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.poubelles">Poubelles</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.toilettes">Toilettes</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.titre">Titre</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.photos">Photos</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMiseAEau.marinas">Marinas</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {psMiseAEauList.map((psMiseAEau, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${psMiseAEau.id}`} color="link" size="sm">
                      {psMiseAEau.id}
                    </Button>
                  </td>
                  <td>{psMiseAEau.lat}</td>
                  <td>{psMiseAEau.lng}</td>
                  <td>{psMiseAEau.access}</td>
                  <td>{psMiseAEau.amenagee ? 'true' : 'false'}</td>
                  <td>{psMiseAEau.commune}</td>
                  <td>{psMiseAEau.lieuDit}</td>
                  <td>{psMiseAEau.parkingPlace}</td>
                  <td>{psMiseAEau.observation}</td>
                  <td>{psMiseAEau.parking ? 'true' : 'false'}</td>
                  <td>{psMiseAEau.payant ? 'true' : 'false'}</td>
                  <td>{psMiseAEau.pointEau ? 'true' : 'false'}</td>
                  <td>{psMiseAEau.poubelles ? 'true' : 'false'}</td>
                  <td>{psMiseAEau.toilettes ? 'true' : 'false'}</td>
                  <td>{psMiseAEau.titre}</td>
                  <td>
                    {psMiseAEau.photos ? (
                      <div>
                        <a onClick={openFile(psMiseAEau.photosContentType, psMiseAEau.photos)}>
                          <img src={`data:${psMiseAEau.photosContentType};base64,${psMiseAEau.photos}`} style={{ maxHeight: '30px' }} />
                          &nbsp;
                        </a>
                        <span>
                          {psMiseAEau.photosContentType}, {byteSize(psMiseAEau.photos)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`pechencApp.PSStatus.${psMiseAEau.status}`} />
                  </td>
                  <td>{psMiseAEau.marinas ? <Link to={`ps-marina/${psMiseAEau.marinas.id}`}>{psMiseAEau.marinas.idMarina}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${psMiseAEau.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${psMiseAEau.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${psMiseAEau.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ psMiseAEau }: IRootState) => ({
  psMiseAEauList: psMiseAEau.entities
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
)(PsMiseAEau);
