'use strict';

describe('Controller Tests', function() {

    describe('Contraceptives Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockContraceptives, MockGynecoobstetricBkg;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockContraceptives = jasmine.createSpy('MockContraceptives');
            MockGynecoobstetricBkg = jasmine.createSpy('MockGynecoobstetricBkg');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Contraceptives': MockContraceptives,
                'GynecoobstetricBkg': MockGynecoobstetricBkg
            };
            createController = function() {
                $injector.get('$controller')("ContraceptivesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineAppApp:contraceptivesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
