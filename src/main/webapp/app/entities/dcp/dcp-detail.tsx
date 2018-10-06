import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './dcp.reducer';
import { IDCP } from 'app/shared/model/dcp.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDCPDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DCPDetail extends React.Component<IDCPDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { dCPEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="pechencApp.dCP.detail.title">DCP</Translate> [<b>{dCPEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nom">
                <Translate contentKey="pechencApp.dCP.nom">Nom</Translate>
              </span>
            </dt>
            <dd>{dCPEntity.nom}</dd>
            <dt>
              <span id="position">
                <Translate contentKey="pechencApp.dCP.position">Position</Translate>
              </span>
            </dt>
            <dd>{dCPEntity.position}</dd>
            <dt>
              <span id="lat">
                <Translate contentKey="pechencApp.dCP.lat">Lat</Translate>
              </span>
            </dt>
            <dd>{dCPEntity.lat}</dd>
            <dt>
              <span id="lng">
                <Translate contentKey="pechencApp.dCP.lng">Lng</Translate>
              </span>
            </dt>
            <dd>{dCPEntity.lng}</dd>
            <dt>
              <span id="dateDerniereMaj">
                <Translate contentKey="pechencApp.dCP.dateDerniereMaj">Date Derniere Maj</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={dCPEntity.dateDerniereMaj} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="etat">
                <Translate contentKey="pechencApp.dCP.etat">Etat</Translate>
              </span>
            </dt>
            <dd>{dCPEntity.etat}</dd>
            <dt>
              <span id="localisation">
                <Translate contentKey="pechencApp.dCP.localisation">Localisation</Translate>
              </span>
            </dt>
            <dd>{dCPEntity.localisation}</dd>
          </dl>
          <Button tag={Link} to="/entity/dcp" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/dcp/${dCPEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ dCP }: IRootState) => ({
  dCPEntity: dCP.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DCPDetail);
