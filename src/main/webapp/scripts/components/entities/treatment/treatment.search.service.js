'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('TreatmentSearch', function ($resource) {
        return $resource('api/_search/treatments/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
