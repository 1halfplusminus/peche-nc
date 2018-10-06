import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './dcp.reducer';
import { IDCP } from 'app/shared/model/dcp.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDCPProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IDCPState {
  search: string;
}

export class DCP extends React.Component<IDCPProps, IDCPState> {
  state: IDCPState = {
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
    const { dCPList, match } = this.props;
    return (
      <div>
        <h2 id="dcp-heading">
          <Translate contentKey="pechencApp.dCP.home.title">DCPS</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="pechencApp.dCP.home.createLabel">Create new DCP</Translate>
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
                    placeholder={translate('pechencApp.dCP.home.search')}
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
                  <Translate contentKey="pechencApp.dCP.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.dCP.position">Position</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.dCP.lat">Lat</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.dCP.lng">Lng</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.dCP.dateDerniereMaj">Date Derniere Maj</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.dCP.etat">Etat</Translate>
                </th>
                <th>
                  <Translate contentKey="pechencApp.dCP.localisation">Localisation</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dCPList.map((dCP, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${dCP.id}`} color="link" size="sm">
                      {dCP.id}
                    </Button>
                  </td>
                  <td>{dCP.nom}</td>
                  <td>{dCP.position}</td>
                  <td>{dCP.lat}</td>
                  <td>{dCP.lng}</td>
                  <td>
                    <TextFormat type="date" value={dCP.dateDerniereMaj} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <Translate contentKey={`pechencApp.DCPEtat.${dCP.etat}`} />
                  </td>
                  <td>{dCP.localisation}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${dCP.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${dCP.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${dCP.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ dCP }: IRootState) => ({
  dCPList: dCP.entities
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
)(DCP);
