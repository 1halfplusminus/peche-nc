import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './markers.reducer';
import { IMarkers } from 'app/shared/model/markers.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMarkersDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class MarkersDetail extends React.Component<IMarkersDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { markersEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="pechencApp.markers.detail.title">Markers</Translate> [<b>{markersEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="icon">
                <Translate contentKey="pechencApp.markers.icon">Icon</Translate>
              </span>
            </dt>
            <dd>
              {markersEntity.icon ? (
                <div>
                  <a onClick={openFile(markersEntity.iconContentType, markersEntity.icon)}>
                    <img src={`data:${markersEntity.iconContentType};base64,${markersEntity.icon}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {markersEntity.iconContentType}, {byteSize(markersEntity.icon)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="titre">
                <Translate contentKey="pechencApp.markers.titre">Titre</Translate>
              </span>
            </dt>
            <dd>{markersEntity.titre}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="pechencApp.markers.description">Description</Translate>
              </span>
            </dt>
            <dd>{markersEntity.description}</dd>
            <dt>
              <span id="email">
                <Translate contentKey="pechencApp.markers.email">Email</Translate>
              </span>
            </dt>
            <dd>{markersEntity.email}</dd>
            <dt>
              <span id="pj">
                <Translate contentKey="pechencApp.markers.pj">Pj</Translate>
              </span>
            </dt>
            <dd>
              {markersEntity.pj ? (
                <div>
                  <a onClick={openFile(markersEntity.pjContentType, markersEntity.pj)}>
                    <img src={`data:${markersEntity.pjContentType};base64,${markersEntity.pj}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {markersEntity.pjContentType}, {byteSize(markersEntity.pj)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="ip">
                <Translate contentKey="pechencApp.markers.ip">Ip</Translate>
              </span>
            </dt>
            <dd>{markersEntity.ip}</dd>
            <dt>
              <span id="lat">
                <Translate contentKey="pechencApp.markers.lat">Lat</Translate>
              </span>
            </dt>
            <dd>{markersEntity.lat}</dd>
            <dt>
              <span id="lng">
                <Translate contentKey="pechencApp.markers.lng">Lng</Translate>
              </span>
            </dt>
            <dd>{markersEntity.lng}</dd>
            <dt>
              <span id="date">
                <Translate contentKey="pechencApp.markers.date">Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={markersEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/markers" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/markers/${markersEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ markers }: IRootState) => ({
  markersEntity: markers.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MarkersDetail);
