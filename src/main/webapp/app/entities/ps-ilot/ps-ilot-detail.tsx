import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ps-ilot.reducer';
import { IPsIlot } from 'app/shared/model/ps-ilot.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPsIlotDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PsIlotDetail extends React.Component<IPsIlotDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { psIlotEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="pechencApp.psIlot.detail.title">PsIlot</Translate> [<b>{psIlotEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="lat">
                <Translate contentKey="pechencApp.psIlot.lat">Lat</Translate>
              </span>
            </dt>
            <dd>{psIlotEntity.lat}</dd>
            <dt>
              <span id="lng">
                <Translate contentKey="pechencApp.psIlot.lng">Lng</Translate>
              </span>
            </dt>
            <dd>{psIlotEntity.lng}</dd>
            <dt>
              <span id="idIlot">
                <Translate contentKey="pechencApp.psIlot.idIlot">Id Ilot</Translate>
              </span>
            </dt>
            <dd>{psIlotEntity.idIlot}</dd>
            <dt>
              <span id="calendrier">
                <Translate contentKey="pechencApp.psIlot.calendrier">Calendrier</Translate>
              </span>
            </dt>
            <dd>{psIlotEntity.calendrier}</dd>
            <dt>
              <span id="commune">
                <Translate contentKey="pechencApp.psIlot.commune">Commune</Translate>
              </span>
            </dt>
            <dd>{psIlotEntity.commune}</dd>
            <dt>
              <span id="posehelico">
                <Translate contentKey="pechencApp.psIlot.posehelico">Posehelico</Translate>
              </span>
            </dt>
            <dd>{psIlotEntity.posehelico}</dd>
            <dt>
              <span id="titre">
                <Translate contentKey="pechencApp.psIlot.titre">Titre</Translate>
              </span>
            </dt>
            <dd>{psIlotEntity.titre}</dd>
            <dt>
              <span id="copyright">
                <Translate contentKey="pechencApp.psIlot.copyright">Copyright</Translate>
              </span>
            </dt>
            <dd>{psIlotEntity.copyright}</dd>
            <dt>
              <span id="photo">
                <Translate contentKey="pechencApp.psIlot.photo">Photo</Translate>
              </span>
            </dt>
            <dd>
              {psIlotEntity.photo ? (
                <div>
                  <a onClick={openFile(psIlotEntity.photoContentType, psIlotEntity.photo)}>
                    <img src={`data:${psIlotEntity.photoContentType};base64,${psIlotEntity.photo}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {psIlotEntity.photoContentType}, {byteSize(psIlotEntity.photo)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="status">
                <Translate contentKey="pechencApp.psIlot.status">Status</Translate>
              </span>
            </dt>
            <dd>{psIlotEntity.status}</dd>
          </dl>
          <Button tag={Link} to="/entity/ps-ilot" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/ps-ilot/${psIlotEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ psIlot }: IRootState) => ({
  psIlotEntity: psIlot.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PsIlotDetail);
