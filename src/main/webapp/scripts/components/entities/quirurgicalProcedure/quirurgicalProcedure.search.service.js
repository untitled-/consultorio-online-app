'use strict';

angular.module('consultorioOnlineAppApp')
    .factory('QuirurgicalProcedureSearch', function ($resource) {
        return $resource('api/_search/quirurgicalProcedures/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
