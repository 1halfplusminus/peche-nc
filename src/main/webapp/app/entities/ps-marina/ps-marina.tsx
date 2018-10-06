import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { openFile, byteSize, Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './ps-marina.reducer';
import { IPsMarina } from 'app/shared/model/ps-marina.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPsMarinaProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IPsMarinaState {
  search: string;
}

export class PsMarina extends React.Component<IPsMarinaProps, IPsMarinaState> {
  state: IPsMarinaState = {
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
    const { psMarinaList, match } = this.props;
    return (
      <div>
        <h2 id="ps-marina-heading">
          <Translate contentKey="pechencApp.psMarina.home.title">Ps Marinas</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="pechencApp.psMarina.home.createLabel">Create new Ps Marina</Translate>
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
                    placeholder={translate('pechencApp.psMarina.home.search')}
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
                  <Translate contentKey="pechencApp.psMarina.lat">Lat</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.lng">Lng</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.idMarina">Id Marina</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.access">Access</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.amenagee">Amenagee</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.commune">Commune</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.lieuDit">Lieu Dit</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.parkingPlace">Parking Place</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.observation">Observation</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.parking">Parking</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.payant">Payant</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.pointEau">Point Eau</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.poubelles">Poubelles</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.toilettes">Toilettes</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.titre">Titre</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.photos">Photos</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.psMarina.status">Status</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {psMarinaList.map((psMarina, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${psMarina.id}`} color="link" size="sm">
                      {psMarina.id}
                    </Button>
                  </td>
                  <td>{psMarina.lat}</td>
                  <td>{psMarina.lng}</td>
                  <td>{psMarina.idMarina}</td>
                  <td>{psMarina.access}</td>
                  <td>{psMarina.amenagee ? 'true' : 'false'}</td>
                  <td>{psMarina.commune}</td>
                  <td>{psMarina.lieuDit}</td>
                  <td>{psMarina.parkingPlace}</td>
                  <td>{psMarina.observation}</td>
                  <td>{psMarina.parking ? 'true' : 'false'}</td>
                  <td>{psMarina.payant ? 'true' : 'false'}</td>
                  <td>{psMarina.pointEau ? 'true' : 'false'}</td>
                  <td>{psMarina.poubelles ? 'true' : 'false'}</td>
                  <td>{psMarina.toilettes ? 'true' : 'false'}</td>
                  <td>{psMarina.titre}</td>
                  <td>
                    {psMarina.photos ? (
                      <div>
                        <a onClick={openFile(psMarina.photosContentType, psMarina.photos)}>
                          <img src={`data:${psMarina.photosContentType};base64,${psMarina.photos}`} style={{ maxHeight: '30px' }} />
                          &nbsp;
                        </a>
                        <span>
                          {psMarina.photosContentType}, {byteSize(psMarina.photos)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`pechencApp.PSStatus.${psMarina.status}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${psMarina.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${psMarina.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${psMarina.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ psMarina }: IRootState) => ({
  psMarinaList: psMarina.entities
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
)(PsMarina);
