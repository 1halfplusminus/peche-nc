import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ps-mise-a-eau.reducer';
import { IPsMiseAEau } from 'app/shared/model/ps-mise-a-eau.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPsMiseAEauDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PsMiseAEauDetail extends React.Component<IPsMiseAEauDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { psMiseAEauEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="pechencApp.psMiseAEau.detail.title">PsMiseAEau</Translate> [<b>{psMiseAEauEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="lat">
                <Translate contentKey="pechencApp.psMiseAEau.lat">Lat</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.lat}</dd>
            <dt>
              <span id="lng">
                <Translate contentKey="pechencApp.psMiseAEau.lng">Lng</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.lng}</dd>
            <dt>
              <span id="access">
                <Translate contentKey="pechencApp.psMiseAEau.access">Access</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.access}</dd>
            <dt>
              <span id="amenagee">
                <Translate contentKey="pechencApp.psMiseAEau.amenagee">Amenagee</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.amenagee ? 'true' : 'false'}</dd>
            <dt>
              <span id="commune">
                <Translate contentKey="pechencApp.psMiseAEau.commune">Commune</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.commune}</dd>
            <dt>
              <span id="lieuDit">
                <Translate contentKey="pechencApp.psMiseAEau.lieuDit">Lieu Dit</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.lieuDit}</dd>
            <dt>
              <span id="parkingPlace">
                <Translate contentKey="pechencApp.psMiseAEau.parkingPlace">Parking Place</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.parkingPlace}</dd>
            <dt>
              <span id="observation">
                <Translate contentKey="pechencApp.psMiseAEau.observation">Observation</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.observation}</dd>
            <dt>
              <span id="parking">
                <Translate contentKey="pechencApp.psMiseAEau.parking">Parking</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.parking ? 'true' : 'false'}</dd>
            <dt>
              <span id="payant">
                <Translate contentKey="pechencApp.psMiseAEau.payant">Payant</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.payant ? 'true' : 'false'}</dd>
            <dt>
              <span id="pointEau">
                <Translate contentKey="pechencApp.psMiseAEau.pointEau">Point Eau</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.pointEau ? 'true' : 'false'}</dd>
            <dt>
              <span id="poubelles">
                <Translate contentKey="pechencApp.psMiseAEau.poubelles">Poubelles</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.poubelles ? 'true' : 'false'}</dd>
            <dt>
              <span id="toilettes">
                <Translate contentKey="pechencApp.psMiseAEau.toilettes">Toilettes</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.toilettes ? 'true' : 'false'}</dd>
            <dt>
              <span id="titre">
                <Translate contentKey="pechencApp.psMiseAEau.titre">Titre</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.titre}</dd>
            <dt>
              <span id="photos">
                <Translate contentKey="pechencApp.psMiseAEau.photos">Photos</Translate>
              </span>
            </dt>
            <dd>
              {psMiseAEauEntity.photos ? (
                <div>
                  <a onClick={openFile(psMiseAEauEntity.photosContentType, psMiseAEauEntity.photos)}>
                    <img
                      src={`data:${psMiseAEauEntity.photosContentType};base64,${psMiseAEauEntity.photos}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {psMiseAEauEntity.photosContentType}, {byteSize(psMiseAEauEntity.photos)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="status">
                <Translate contentKey="pechencApp.psMiseAEau.status">Status</Translate>
              </span>
            </dt>
            <dd>{psMiseAEauEntity.status}</dd>
            <dt>
              <Translate contentKey="pechencApp.psMiseAEau.marinas">Marinas</Translate>
            </dt>
            <dd>{psMiseAEauEntity.marinas ? psMiseAEauEntity.marinas.idMarina : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/ps-mise-a-eau" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/ps-mise-a-eau/${psMiseAEauEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ psMiseAEau }: IRootState) => ({
  psMiseAEauEntity: psMiseAEau.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PsMiseAEauDetail);
