import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ps-marina.reducer';
import { IPsMarina } from 'app/shared/model/ps-marina.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPsMarinaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PsMarinaDetail extends React.Component<IPsMarinaDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { psMarinaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="pechencApp.psMarina.detail.title">PsMarina</Translate> [<b>{psMarinaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="lat">
                <Translate contentKey="pechencApp.psMarina.lat">Lat</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.lat}</dd>
            <dt>
              <span id="lng">
                <Translate contentKey="pechencApp.psMarina.lng">Lng</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.lng}</dd>
            <dt>
              <span id="idMarina">
                <Translate contentKey="pechencApp.psMarina.idMarina">Id Marina</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.idMarina}</dd>
            <dt>
              <span id="access">
                <Translate contentKey="pechencApp.psMarina.access">Access</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.access}</dd>
            <dt>
              <span id="amenagee">
                <Translate contentKey="pechencApp.psMarina.amenagee">Amenagee</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.amenagee ? 'true' : 'false'}</dd>
            <dt>
              <span id="commune">
                <Translate contentKey="pechencApp.psMarina.commune">Commune</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.commune}</dd>
            <dt>
              <span id="lieuDit">
                <Translate contentKey="pechencApp.psMarina.lieuDit">Lieu Dit</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.lieuDit}</dd>
            <dt>
              <span id="parkingPlace">
                <Translate contentKey="pechencApp.psMarina.parkingPlace">Parking Place</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.parkingPlace}</dd>
            <dt>
              <span id="observation">
                <Translate contentKey="pechencApp.psMarina.observation">Observation</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.observation}</dd>
            <dt>
              <span id="parking">
                <Translate contentKey="pechencApp.psMarina.parking">Parking</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.parking ? 'true' : 'false'}</dd>
            <dt>
              <span id="payant">
                <Translate contentKey="pechencApp.psMarina.payant">Payant</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.payant ? 'true' : 'false'}</dd>
            <dt>
              <span id="pointEau">
                <Translate contentKey="pechencApp.psMarina.pointEau">Point Eau</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.pointEau ? 'true' : 'false'}</dd>
            <dt>
              <span id="poubelles">
                <Translate contentKey="pechencApp.psMarina.poubelles">Poubelles</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.poubelles ? 'true' : 'false'}</dd>
            <dt>
              <span id="toilettes">
                <Translate contentKey="pechencApp.psMarina.toilettes">Toilettes</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.toilettes ? 'true' : 'false'}</dd>
            <dt>
              <span id="titre">
                <Translate contentKey="pechencApp.psMarina.titre">Titre</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.titre}</dd>
            <dt>
              <span id="photos">
                <Translate contentKey="pechencApp.psMarina.photos">Photos</Translate>
              </span>
            </dt>
            <dd>
              {psMarinaEntity.photos ? (
                <div>
                  <a onClick={openFile(psMarinaEntity.photosContentType, psMarinaEntity.photos)}>
                    <img src={`data:${psMarinaEntity.photosContentType};base64,${psMarinaEntity.photos}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {psMarinaEntity.photosContentType}, {byteSize(psMarinaEntity.photos)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="status">
                <Translate contentKey="pechencApp.psMarina.status">Status</Translate>
              </span>
            </dt>
            <dd>{psMarinaEntity.status}</dd>
          </dl>
          <Button tag={Link} to="/entity/ps-marina" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/ps-marina/${psMarinaEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ psMarina }: IRootState) => ({
  psMarinaEntity: psMarina.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PsMarinaDetail);
