'use strict';

angular.module('consultorioOnlineUiApp')
    .factory('QuirurgicalProcedureSearch', function ($resource) {
        return $resource('api/_search/quirurgicalProcedures/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
