'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('ConsultationSearch', function ($resource) {
        return $resource('api/_search/consultations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
