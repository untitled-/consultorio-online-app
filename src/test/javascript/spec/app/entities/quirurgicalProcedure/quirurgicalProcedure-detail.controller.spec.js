'use strict';

describe('Controller Tests', function() {

    describe('QuirurgicalProcedure Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockQuirurgicalProcedure, MockPathologicBkg;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockQuirurgicalProcedure = jasmine.createSpy('MockQuirurgicalProcedure');
            MockPathologicBkg = jasmine.createSpy('MockPathologicBkg');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'QuirurgicalProcedure': MockQuirurgicalProcedure,
                'PathologicBkg': MockPathologicBkg
            };
            createController = function() {
                $injector.get('$controller')("QuirurgicalProcedureDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineUiApp:quirurgicalProcedureUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
