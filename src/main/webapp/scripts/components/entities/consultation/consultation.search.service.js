'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('ConsultationSearch', function ($resource) {
        return $resource('api/_search/consultations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
