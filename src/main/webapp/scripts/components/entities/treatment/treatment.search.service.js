'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('TreatmentSearch', function ($resource) {
        return $resource('api/_search/treatments/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
